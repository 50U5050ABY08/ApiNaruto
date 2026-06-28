import { useState } from 'react'
import './App.css'

interface Ninja {
  id: number
  nome: string
  email: string
  idade: number
  missao: string | null
}

interface AuthResponse {
  token: string
}

function App() {
  const apiUrl = import.meta.env.VITE_API_URL
  const titulo: string = 'API Naruto'

  const [username, setUsername] = useState<string>('')
  const [password, setPassword] = useState<string>('')

  const [token, setToken] = useState<string>('')
  const [mensagem, setMensagem] = useState<string>(
    'Faça login para acessar os ninjas.'
  )

  const [ninjas, setNinjas] = useState<Ninja[]>([])

  async function fazerLogin() {
    setMensagem('Realizando login...')

    try {
      const resposta = await fetch(`${apiUrl}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: username,
          password: password,
        }),
      })

      if (!resposta.ok) {
        setMensagem(`Erro no login. Status: ${resposta.status}`)
        return
      }

      const dados: AuthResponse = await resposta.json()

      setToken(dados.token)
      setMensagem('Login realizado com sucesso.')
    } catch (erro) {
      console.error('Erro ao fazer login:', erro)
      setMensagem('Erro de conexão com a API ao fazer login.')
    }
  }

  async function listarNinjas() {
    if (!token) {
      setMensagem('Você precisa fazer login antes de listar os ninjas.')
      return
    }

    setMensagem('Buscando ninjas da API...')

    try {
      const resposta = await fetch(`${apiUrl}/ninjas`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })

      if (!resposta.ok) {
        setMensagem(`Erro ao buscar ninjas. Status: ${resposta.status}`)
        return
      }

      const dados = await resposta.json()

      setNinjas(dados.content)
      setMensagem('Ninjas carregados com sucesso.')
    } catch (erro) {
      console.error('Erro ao conectar com a API:', erro)
      setMensagem('Erro de conexão com a API. Verifique o backend ou CORS.')
    }
  }

  function sair() {
    setToken('')
    setNinjas([])
    setMensagem('Você saiu da aplicação.')
  }

  return (
    <main>
      <h1>{titulo}</h1>

      <p>
        Front-end desenvolvido com React e TypeScript.
      </p>

      <section>
        <h2>Login</h2>

        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(event) => setUsername(event.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />

        <button onClick={fazerLogin}>
          Entrar
        </button>

        <button onClick={sair}>
          Sair
        </button>
      </section>

      <section>
        <h2>Ninjas</h2>

        <button onClick={listarNinjas}>
          Listar ninjas
        </button>

        <p>{mensagem}</p>

        <ul>
          {ninjas.map((ninja) => (
            <li key={ninja.id}>
              {ninja.nome} - {ninja.idade} anos - Missão:{' '}
              {ninja.missao ?? 'Sem missão'}
            </li>
          ))}
        </ul>
      </section>
    </main>
  )
}

export default App