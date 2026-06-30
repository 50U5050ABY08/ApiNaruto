import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import { login } from './authService'

describe('authService', () => {
  beforeEach(() => {
    vi.stubGlobal('fetch', vi.fn())
  })

  afterEach(() => {
    vi.unstubAllGlobals()
  })

  it('deve realizar login com sucesso', async () => {
    const fetchMock = vi.mocked(fetch)

    fetchMock.mockResolvedValueOnce(
      new Response(
        JSON.stringify({
          token: 'jwt-token',
          role: 'ROLE_ADMIN',
        }),
        {
          status: 200,
          headers: {
            'Content-Type': 'application/json',
          },
        },
      ),
    )

    const response = await login({
      username: 'breno',
      password: '123456',
    })

    expect(response.token).toBe('jwt-token')
    expect(response.role).toBe('ROLE_ADMIN')

    expect(fetchMock).toHaveBeenCalledWith(
      'http://localhost:8080/auth/login',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: 'breno',
          password: '123456',
        }),
      },
    )
  })

  it('deve lançar erro quando o login falhar', async () => {
    const fetchMock = vi.mocked(fetch)

    fetchMock.mockResolvedValueOnce(
      new Response(
        JSON.stringify({
          message: 'Usuário ou senha inválidos.',
        }),
        {
          status: 401,
          headers: {
            'Content-Type': 'application/json',
          },
        },
      ),
    )

    await expect(
      login({
        username: 'breno',
        password: 'errada',
      }),
    ).rejects.toThrow('Usuário ou senha inválidos.')
  })
})