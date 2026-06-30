import { describe, expect, it, vi } from 'vitest'
import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import LoginForm from './LoginForm'

describe('LoginForm', () => {
  it('deve mostrar status Não autenticado quando usuário não estiver logado', () => {
    render(
      <LoginForm
        username=""
        password=""
        isAuthenticated={false}
        isLoading={false}
        onUsernameChange={vi.fn()}
        onPasswordChange={vi.fn()}
        onLogin={vi.fn()}
        onLogout={vi.fn()}
      />,
    )

    expect(screen.getByText('Não autenticado')).toBeInTheDocument()
    expect(screen.getByRole('button', { name: 'Entrar' })).toBeEnabled()
    expect(screen.getByRole('button', { name: 'Sair' })).toBeDisabled()
  })

  it('deve mostrar status Autenticado quando usuário estiver logado', () => {
    render(
      <LoginForm
        username="breno"
        password=""
        isAuthenticated={true}
        isLoading={false}
        onUsernameChange={vi.fn()}
        onPasswordChange={vi.fn()}
        onLogin={vi.fn()}
        onLogout={vi.fn()}
      />,
    )

    expect(screen.getByText('Autenticado')).toBeInTheDocument()
    expect(screen.getByRole('button', { name: 'Entrar' })).toBeDisabled()
    expect(screen.getByRole('button', { name: 'Sair' })).toBeEnabled()
  })

  it('deve chamar onLogin ao clicar em Entrar', async () => {
    const user = userEvent.setup()
    const onLogin = vi.fn()

    render(
      <LoginForm
        username="breno"
        password="123456"
        isAuthenticated={false}
        isLoading={false}
        onUsernameChange={vi.fn()}
        onPasswordChange={vi.fn()}
        onLogin={onLogin}
        onLogout={vi.fn()}
      />,
    )

    await user.click(screen.getByRole('button', { name: 'Entrar' }))

    expect(onLogin).toHaveBeenCalledTimes(1)
  })

  it('deve chamar callbacks ao digitar username e password', async () => {
    const user = userEvent.setup()
    const onUsernameChange = vi.fn()
    const onPasswordChange = vi.fn()

    render(
      <LoginForm
        username=""
        password=""
        isAuthenticated={false}
        isLoading={false}
        onUsernameChange={onUsernameChange}
        onPasswordChange={onPasswordChange}
        onLogin={vi.fn()}
        onLogout={vi.fn()}
      />,
    )

    await user.type(screen.getByLabelText('Username'), 'breno')
    await user.type(screen.getByLabelText('Password'), '123456')

    expect(onUsernameChange).toHaveBeenCalled()
    expect(onPasswordChange).toHaveBeenCalled()
  })
})