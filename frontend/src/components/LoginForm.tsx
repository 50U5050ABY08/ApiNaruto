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
  <section className="card">
    <div className="card-header">
      <h2>Login</h2>

      <span
        className={
          isAuthenticated
            ? 'status status-success'
            : 'status status-warning'
        }
      >
        {isAuthenticated ? 'Autenticado' : 'Não autenticado'}
      </span>
    </div>

    <div className="form-group">
      <label htmlFor="username">Username</label>

      <input
        id="username"
        type="text"
        placeholder="Digite seu usuário"
        value={username}
        disabled={isLoading || isAuthenticated}
        onChange={(event) => onUsernameChange(event.target.value)}
      />
    </div>

    <div className="form-group">
      <label htmlFor="password">Password</label>

      <input
        id="password"
        type="password"
        placeholder="Digite sua senha"
        value={password}
        disabled={isLoading || isAuthenticated}
        onChange={(event) => onPasswordChange(event.target.value)}
      />
    </div>

    <div className="button-group">
      <button
        className="button button-primary"
        onClick={onLogin}
        disabled={isLoading || isAuthenticated}
      >
        Entrar
      </button>

      <button
        className="button button-secondary"
        onClick={onLogout}
        disabled={isLoading || !isAuthenticated}
      >
        Sair
      </button>
    </div>
  </section>
)
}

export default LoginForm

/**
 * props
→ propriedades
→ dados/funções que um componente recebe de fora

MELHORA NO CÓDIGO:
label
→ texto ligado ao input
→ melhora acessibilidade

htmlFor="username"
→ conecta o label ao input id="username"

status visual
→ mostra se está autenticado

button-group
→ organiza os botões
 */