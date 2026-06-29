import { API_URL } from './api'
import type { Missao } from '../types/missao'

export async function buscarMissoes(
  token: string,
): Promise<Missao[]> {
  const response = await fetch(`${API_URL}/missoes`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })

  if (!response.ok) {
    throw new Error(`Erro ao buscar missões. Status: ${response.status}`)
  }

  return response.json()
}