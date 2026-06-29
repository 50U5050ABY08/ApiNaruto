interface LoginFormProps {
  username: string
  password: string
  isAuthenticated: boolean
  isLoading: boolean
  onUsernameChange: (value: string) => void
  onPasswordChange: (value: string) => void
  onLogin: () => void
  onLogout: () => void
}

function LoginForm({
  username,
  password,
  isAuthenticated,
  isLoading,
  onUsernameChange,
  onPasswordChange,
  onLogin,
  onLogout,
}: LoginFormProps) {
  return (
    <section>
      <h2>Login</h2>

      <p>
        Status:{' '}
        <strong>
          {isAuthenticated ? 'Autenticado' : 'Não autenticado'}
        </strong>
      </p>

      <input
        type="text"
        placeholder="Username"
        value={username}
        disabled={isLoading || isAuthenticated}
        onChange={(event) => onUsernameChange(event.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        disabled={isLoading || isAuthenticated}
        onChange={(event) => onPasswordChange(event.target.value)}
      />

      <button
        onClick={onLogin}
        disabled={isLoading || isAuthenticated}
      >
        Entrar
      </button>

      <button
        onClick={onLogout}
        disabled={isLoading || !isAuthenticated}
      >
        Sair
      </button>
    </section>
  )
}

export default LoginForm

/**
 * props
→ propriedades
→ dados/funções que um componente recebe de fora
 */