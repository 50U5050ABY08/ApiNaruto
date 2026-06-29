import { API_URL } from './api'
import type { AuthRequest, AuthResponse } from '../types/auth'
import { extractApiErrorMessage } from '../utils/apiError'

export async function login(
  authRequest: AuthRequest,
): Promise<AuthResponse> {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(authRequest),
  })

  if (!response.ok) {
    const message = await extractApiErrorMessage(response)
    throw new Error(message)
  }

  return response.json()
}

/**
 * service
 * 
 * serviço ---> arquivo responsável por uma parte da comunicação com a API.
 * 
 * ========================================================================================
 * 
 * Promise<AuthResponse> ---> promessa de que no futuro virá uma resposta do tipo AuthResponse. 
 */