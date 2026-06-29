interface LoginFormProps {
  username: string
  password: string
  onUsernameChange: (value: string) => void
  onPasswordChange: (value: string) => void
  onLogin: () => void
  onLogout: () => void
}

function LoginForm({
  username,
  password,
  onUsernameChange,
  onPasswordChange,
  onLogin,
  onLogout,
}: LoginFormProps)  {
  return (
    <section>
      <h2>Login</h2>

      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(event) => onUsernameChange(event.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(event) => onPasswordChange(event.target.value)}
      />

      <button onClick={onLogin}>
        Entrar
      </button>

      <button onClick={onLogout}>
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