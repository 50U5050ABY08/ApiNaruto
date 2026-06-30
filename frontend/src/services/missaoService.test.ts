import { afterEach, beforeEach, describe, expect, it, vi } from 'vitest'
import { buscarMissoes } from './missaoService'


describe('NinjaService', () => {
  beforeEach(() => {
    vi.stubGlobal('fetch', vi.fn())
  })

    afterEach(() => {
    vi.unstubAllGlobals()
  })

  it('deve buscar missões com token JWT', async () => {
    const fetchMock = vi.mocked(fetch)

    fetchMock.mockResolvedValueOnce(
      new Response(
        JSON.stringify([
            {
                id: 2,
                missao: 'Exame Chunin',
                rankingDaMissao: 'B',
            },
            {
                id: 1,
                missao: 'Proteger a Ponte Atualizada',
                rankingDaMissao: 'A',
            },
        ]),
        {
          status: 200,
          headers: {
            'Content-Type': 'application/json',
          },
        },
      ),
    )

    const missoes = await buscarMissoes('jwt-token')

    expect(missoes).toHaveLength(2)
    expect(missoes[0].missao).toBe('Exame Chunin')

    expect(fetchMock).toHaveBeenCalledWith(
      'http://localhost:8080/missoes',
      {
        headers: {
            Authorization: 'Bearer jwt-token',
        },
      },
    )
  })

  it('deve lançar erro quando a busca de missões falhar', async () => {
    const fetchMock = vi.mocked(fetch)

    fetchMock.mockResolvedValueOnce(
      new Response(
        JSON.stringify({
            message: 'Erro ao buscar missões',
        }),
        {
          status: 500,
          headers: {
            'Content-Type': 'application/json',
          },
        },
      ),
    )

    await expect(
        buscarMissoes('jwt-token'),
    ).rejects.toThrow('Erro ao buscar missões')
  })
})