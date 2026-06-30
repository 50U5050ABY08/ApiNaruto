import { beforeEach, describe, expect, it, vi } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import App from './App'
import { login } from './services/authService'
import { buscarMissoes } from './services/missaoService'
import { buscarNinjas } from './services/ninjaService'

vi.mock('./services/authService', () => ({
    login: vi.fn(),
}))

vi.mock('./services/missaoService', () => ({
    buscarMissoes: vi.fn(),
}))

vi.mock('./services/ninjaService', () => ({
    buscarNinjas: vi.fn(),
    criarNinja: vi.fn(),
    atualizarNinja: vi.fn(),
    deletarNinja: vi.fn(),
}))

describe('App', () => {
    beforeEach(() => {
        vi.clearAllMocks()
})

it('deve realizar login com sucesso e buscar missões', async () => {
    const user = userEvent.setup()

vi.mocked(login).mockResolvedValueOnce({
  token: 'jwt-token',
  role: 'ROLE_ADMIN',
})

vi.mocked(buscarMissoes).mockResolvedValueOnce([
    {
        id: 1,
        missao: 'Proteger a Ponte Atualizada',
        rankingDaMissao: 'A',
    },
])

render(<App />)

await user.type(screen.getByLabelText('Username'), 'breno')
await user.type(screen.getByLabelText('Password'), '123456')


await user.click(screen.getByRole('button', { name: 'Entrar'}))

await waitFor(() => {
    expect(login).toHaveBeenCalledWith({
        username: 'breno',
        password: '123456',
    })
})

await waitFor(() => {
    expect(buscarMissoes).toHaveBeenCalledWith('jwt-token')
})

expect(screen.getByText('Autenticado')).toBeInTheDocument()
})


it('deve listar ninjas após login', async () => {
    const user = userEvent.setup()

    vi.mocked(login).mockResolvedValueOnce({
        token: 'jwt-token',
        role: 'ROLE_ADMIN',
    })

    vi.mocked(buscarMissoes).mockResolvedValueOnce([
        {
            id: 1,
            missao: 'Proteger a Ponte Atualizada',
            rankingDaMissao: 'A',
        },
    ])

    vi.mocked(buscarNinjas).mockResolvedValueOnce([
        {
            id: 1,
            nome: 'Naruto Uzumaki',
            email: 'naruto@folha.com',
            idade: 16,
            missao: 'Proteger a Ponte Atualizada',
        },
    ])

    render(<App />)

    await user.type(screen.getByLabelText('Username'), 'breno')
    await user.type(screen.getByLabelText('Password'), '123456')

    await user.click(screen.getByRole('button', { name: 'Entrar' }))

    await waitFor(() => {
        expect(screen.getByText('Autenticado')).toBeInTheDocument()
    })

    await user.click(screen.getByRole('button', { name: 'Listar ninjas' }))

    await waitFor(() => {
        expect(buscarNinjas).toHaveBeenCalledWith('jwt-token')
    })

    expect(screen.getByText('Naruto Uzumaki')).toBeInTheDocument()
    expect(screen.getByText('naruto@folha.com')).toBeInTheDocument()
    })

    it('deve mostrar mensagem de erro quando login falhar', async () => {
        const user = userEvent.setup()

        vi.mocked(login).mockRejectedValueOnce(
            new Error('Usuário ou senha inválidos.'),
        )

        render(<App />)

        await user.type(screen.getByLabelText('Username'), 'breno')
        await user.type(screen.getByLabelText('Password'), 'errada')

        await user.click(screen.getByRole('button', { name: 'Entrar' }))


        await waitFor(() => {
      expect(
        screen.getByText('Usuário ou senha inválidos.'),
      ).toBeInTheDocument()
    })

    expect(screen.getByText('Não autenticado')).toBeInTheDocument()
  })
})