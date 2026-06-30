import { describe, expect, it } from 'vitest'
import { extractApiErrorMessage } from './apiError'

describe('extractApiErrorMessage', () => {
  it('deve retornar mensagens de campos quando fields existir', async () => {
    const response = new Response(
      JSON.stringify({
        fields: {
          nome: 'O nome é obrigatório',
          email: 'O e-mail deve ser válido',
        },
      }),
      {
        status: 400,
        headers: {
          'Content-Type': 'application/json',
        },
      },
    )

    const message = await extractApiErrorMessage(response)

    expect(message).toBe(
      'O nome é obrigatório O e-mail deve ser válido',
    )
  })

  it('deve retornar message quando existir no erro da API', async () => {
    const response = new Response(
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
    )

    const message = await extractApiErrorMessage(response)

    expect(message).toBe(
      'Ninjas menores de 18 anos não podem participar de missões Rank A.',
    )
  })

  it('deve retornar mensagem padrão quando o corpo não for JSON válido', async () => {
    const response = new Response('erro inesperado', {
      status: 500,
    })

    const message = await extractApiErrorMessage(response)

    expect(message).toBe('Erro na requisição. Status: 500')
  })
})