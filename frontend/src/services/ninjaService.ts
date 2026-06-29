import { API_URL } from './api'
import type { Ninja, NinjaRequest, PageResponse } from '../types/ninja'

export async function buscarNinjas(
  token: string,
): Promise<Ninja[]> {
  const response = await fetch(`${API_URL}/ninjas`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })

  if (!response.ok) {
    throw new Error(`Erro ao buscar ninjas. Status: ${response.status}`)
  }

  const data: PageResponse<Ninja> = await response.json()

  return data.content
}

export async function criarNinja(
  token: string,
  ninjaRequest: NinjaRequest,
): Promise<Ninja> {
  const response = await fetch(`${API_URL}/ninjas`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(ninjaRequest),
  })

  if (!response.ok) {
    throw new Error(`Erro ao criar ninja. Status: ${response.status}`)
  }

  return response.json()
}

/**ESSE SERVICE FAZ:
 * 
 * Recebe token
↓
chama GET /ninjas
↓
valida se deu erro
↓
converte JSON
↓
retorna apenas a lista de ninjas

Assim o App.tsx nã precisa saber detalhes da paginação.
 */