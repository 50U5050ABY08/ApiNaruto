import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import {
  atualizarNinja,
  buscarNinjas,
  criarNinja,
  deletarNinja,
} from './ninjaService'

describe('ninjaService', () => {
  beforeEach(() => {
    vi.stubGlobal('fetch', vi.fn())
  })

  afterEach(() => {
    vi.unstubAllGlobals()
  })

  it('deve buscar ninjas com token JWT', async () => {
    const fetchMock = vi.mocked(fetch)

    fetchMock.mockResolvedValueOnce(
      new Response(
        JSON.stringify({
          content: [
            {
              id: 1,
              nome: 'Naruto Uzumaki',
              email: 'naruto@folha.com',
              idade: 16,
              missao: 'Proteger a Ponte Atualizada',
            },
          ],
        }),
        {
          status: 200,
          headers: {
            'Content-Type': 'application/json',
          },
        },
      ),
    )

    const ninjas = await buscarNinjas('jwt-token')

    expect(ninjas).toHaveLength(1)
    expect(ninjas[0].nome).toBe('Naruto Uzumaki')

    expect(fetchMock).toHaveBeenCalledWith(
      'http://localhost:8080/ninjas',
      {
        headers: {
          Authorization: 'Bearer jwt-token',
        },
      },
    )
  })

  it('deve criar ninja com POST', async () => {
    const fetchMock = vi.mocked(fetch)

    const ninjaRequest = {
      nome: 'Hinata Hyuga',
      email: 'hinata@konoha.com',
      idade: 18,
      missaoId: 2,
    }

    fetchMock.mockResolvedValueOnce(
      new Response(
        JSON.stringify({
          id: 10,
          nome: 'Hinata Hyuga',
          email: 'hinata@konoha.com',
          idade: 18,
          missao: 'Exame Chunin',
        }),
        {
          status: 201,
          headers: {
            'Content-Type': 'application/json',
          },
        },
      ),
    )

    const ninja = await criarNinja('jwt-token', ninjaRequest)

    expect(ninja.id).toBe(10)
    expect(ninja.nome).toBe('Hinata Hyuga')

    expect(fetchMock).toHaveBeenCalledWith(
      'http://localhost:8080/ninjas',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer jwt-token',
        },
        body: JSON.stringify(ninjaRequest),
      },
    )
  })

  it('deve atualizar ninja com PUT', async () => {
    const fetchMock = vi.mocked(fetch)

    const ninjaRequest = {
      nome: 'Naruto Atualizado',
      email: 'naruto@folha.com',
      idade: 17,
      missaoId: 1,
    }

    fetchMock.mockResolvedValueOnce(
      new Response(
        JSON.stringify({
          id: 1,
          nome: 'Naruto Atualizado',
          email: 'naruto@folha.com',
          idade: 17,
          missao: 'Proteger a Ponte Atualizada',
        }),
        {
          status: 200,
          headers: {
            'Content-Type': 'application/json',
          },
        },
      ),
    )

    const ninja = await atualizarNinja(
      'jwt-token',
      1,
      ninjaRequest,
    )

    expect(ninja.id).toBe(1)
    expect(ninja.nome).toBe('Naruto Atualizado')

    expect(fetchMock).toHaveBeenCalledWith(
      'http://localhost:8080/ninjas/1',
      {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer jwt-token',
        },
        body: JSON.stringify(ninjaRequest),
      },
    )
  })

  it('deve deletar ninja com DELETE', async () => {
    const fetchMock = vi.mocked(fetch)

    fetchMock.mockResolvedValueOnce(
      new Response(null, {
        status: 204,
      }),
    )

    await deletarNinja('jwt-token', 1)

    expect(fetchMock).toHaveBeenCalledWith(
      'http://localhost:8080/ninjas/1',
      {
        method: 'DELETE',
        headers: {
          Authorization: 'Bearer jwt-token',
        },
      },
    )
  })

  it('deve lançar erro quando o backend recusar criação de ninja', async () => {
    const fetchMock = vi.mocked(fetch)

    fetchMock.mockResolvedValueOnce(
      new Response(
        JSON.stringify({
          message:
            'Ninjas menores de 18 anos não podem participar de missões Rank A.',
        }),
        {
          status: 400,
          headers: {
            'Content-Type': 'application/json',
          },
        },
      ),
    )

    await expect(
      criarNinja('jwt-token', {
        nome: 'Konohamaru',
        email: 'konohamaru@konoha.com',
        idade: 12,
        missaoId: 1,
      }),
    ).rejects.toThrow(
      'Ninjas menores de 18 anos não podem participar de missões Rank A.',
    )
  })
})