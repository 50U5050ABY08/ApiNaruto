import { describe, expect, it, vi } from 'vitest'
import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import NinjaList from './NinjaList'
import type { Ninja } from '../types/ninja'

const ninjas: Ninja[] = [
  {
    id: 1,
    nome: 'Naruto Uzumaki',
    email: 'naruto@folha.com',
    idade: 16,
    missao: 'Proteger a Ponte Atualizada',
  },
  {
    id: 2,
    nome: 'Kakashi Hatake',
    email: 'kakashi@konoha.com',
    idade: 30,
    missao: 'Exame Chunin',
  },
]

describe('NinjaList', () => {
  it('deve desabilitar botão Listar ninjas quando usuário não estiver autenticado', () => {
    render(
      <NinjaList
        ninjas={[]}
        mensagem="Faça login para acessar os ninjas."
        isAuthenticated={false}
        isAdmin={false}
        isLoading={false}
        onListarNinjas={vi.fn()}
        onEditarNinja={vi.fn()}
        onExcluirNinja={vi.fn()}
      />,
    )

    expect(
      screen.getByRole('button', { name: 'Listar ninjas' }),
    ).toBeDisabled()
  })

  it('deve mostrar tabela de ninjas quando usuário estiver autenticado', () => {
    render(
      <NinjaList
        ninjas={ninjas}
        mensagem="Ninjas carregados com sucesso."
        isAuthenticated={true}
        isAdmin={false}
        isLoading={false}
        onListarNinjas={vi.fn()}
        onEditarNinja={vi.fn()}
        onExcluirNinja={vi.fn()}
      />,
    )

    expect(screen.getByText('Naruto Uzumaki')).toBeInTheDocument()
    expect(screen.getByText('Kakashi Hatake')).toBeInTheDocument()
    expect(screen.getByText('naruto@folha.com')).toBeInTheDocument()
    expect(screen.getByText('Exame Chunin')).toBeInTheDocument()
  })

  it('deve esconder botão Excluir para ROLE_USER', () => {
    render(
      <NinjaList
        ninjas={ninjas}
        mensagem="Ninjas carregados com sucesso."
        isAuthenticated={true}
        isAdmin={false}
        isLoading={false}
        onListarNinjas={vi.fn()}
        onEditarNinja={vi.fn()}
        onExcluirNinja={vi.fn()}
      />,
    )

    expect(screen.queryByRole('button', { name: 'Excluir' })).not.toBeInTheDocument()
    expect(screen.getAllByRole('button', { name: 'Editar' })).toHaveLength(2)
  })

  it('deve mostrar botão Excluir para ROLE_ADMIN', () => {
    render(
      <NinjaList
        ninjas={ninjas}
        mensagem="Ninjas carregados com sucesso."
        isAuthenticated={true}
        isAdmin={true}
        isLoading={false}
        onListarNinjas={vi.fn()}
        onEditarNinja={vi.fn()}
        onExcluirNinja={vi.fn()}
      />,
    )

    expect(screen.getAllByRole('button', { name: 'Excluir' })).toHaveLength(2)
  })

  it('deve chamar onEditarNinja ao clicar em Editar', async () => {
    const user = userEvent.setup()
    const onEditarNinja = vi.fn()

    render(
      <NinjaList
        ninjas={ninjas}
        mensagem="Ninjas carregados com sucesso."
        isAuthenticated={true}
        isAdmin={true}
        isLoading={false}
        onListarNinjas={vi.fn()}
        onEditarNinja={onEditarNinja}
        onExcluirNinja={vi.fn()}
      />,
    )

    await user.click(screen.getAllByRole('button', { name: 'Editar' })[0])

    expect(onEditarNinja).toHaveBeenCalledWith(ninjas[0])
  })

  it('deve chamar onExcluirNinja ao clicar em Excluir como ADMIN', async () => {
    const user = userEvent.setup()
    const onExcluirNinja = vi.fn()

    render(
      <NinjaList
        ninjas={ninjas}
        mensagem="Ninjas carregados com sucesso."
        isAuthenticated={true}
        isAdmin={true}
        isLoading={false}
        onListarNinjas={vi.fn()}
        onEditarNinja={vi.fn()}
        onExcluirNinja={onExcluirNinja}
      />,
    )

    await user.click(screen.getAllByRole('button', { name: 'Excluir' })[0])

    expect(onExcluirNinja).toHaveBeenCalledWith(1)
  })
})