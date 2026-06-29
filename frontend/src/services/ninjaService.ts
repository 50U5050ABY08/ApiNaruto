import { API_URL } from './api'
import type { Ninja, NinjaRequest, PageResponse } from '../types/ninja'
import { extractApiErrorMessage } from '../utils/apiError'

export async function buscarNinjas(
  token: string,
): Promise<Ninja[]> {
  const response = await fetch(`${API_URL}/ninjas`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })

  if (!response.ok) {
    const message = await extractApiErrorMessage(response)
    throw new Error(message)
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
    const message = await extractApiErrorMessage(response)
    throw new Error(message)
  }

  return response.json()
}

export async function deletarNinja(
  token: string,
  ninjaId: number,
): Promise<void> {
  const response = await fetch(`${API_URL}/ninjas/${ninjaId}`, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })

  if (!response.ok) {
    const message = await extractApiErrorMessage(response)  
    throw new Error(message)
  }
}

export async function atualizarNinja(
  token: string,
  ninjaId: number,
  ninjaRequest: NinjaRequest,
): Promise<Ninja> {
  const response = await fetch(`${API_URL}/ninjas/${ninjaId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(ninjaRequest),
  })

  if (!response.ok) {
    const message = await extractApiErrorMessage(response)
    throw new Error(message)
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