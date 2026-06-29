export interface Ninja {
  id: number
  nome: string
  email: string
  idade: number
  missao: string | null
}

export interface NinjaRequest {
  nome: string
  email: string
  idade: number
  missaoId: number
}

export interface PageResponse<T> {
  content: T[]
  empty: boolean
  first: boolean
  last: boolean
  number: number
  numberOfElements: number
  size: number
  totalElements: number
  totalPages: number
}

//interface ---> contrato de formato

//AuthRequest ---> requisição de autenticação

//AuthResponse ---> resposta de autenticação

//PageResponse<T> ---> resposta de paginação genérica, onde T é o tipo de dado que será retornado na página.