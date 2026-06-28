import { useState } from 'react'
import './App.css'
import { login } from './services/authService'
import { buscarNinjas } from './services/ninjaService.ts'
import type { Ninja } from './types/ninja'

function App() {
  const titulo: string = 'API Naruto'

  const [username, setUsername] = useState<string>('')
  const [password, setPassword] = useState<string>('')

  const [token, setToken] = useState<string>('')
  const [mensagem, setMensagem] = useState<string>(
    'Faça login para acessar os ninjas.',
  )

  const [ninjas, setNinjas] = useState<Ninja[]>([])

  async function fazerLogin() {
    setMensagem('Realizando login...')

    try {
      const response = await login({
        username,
        password,
      })

      setToken(response.token)
      setMensagem('Login realizado com sucesso.')
    } catch (error) {
      console.error(error)
      setMensagem('Usuário ou senha inválidos.')
    }
  }

  async function listarNinjas() {
    if (!token) {
      setMensagem('Você precisa fazer login antes de listar os ninjas.')
      return
    }

    setMensagem('Buscando ninjas da API...')

    try {
      const ninjasEncontrados = await buscarNinjas(token)

      setNinjas(ninjasEncontrados)
      setMensagem('Ninjas carregados com sucesso.')
    } catch (error) {
      console.error(error)
      setMensagem('Erro ao buscar ninjas.')
    }
  }

  function sair() {
    setToken('')
    setNinjas([])
    setUsername('')
    setPassword('')
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

/**atualização do código
 * 
 * ANTES:
 * App.tsx
→ tela
→ login
→ fetch
→ regra de chamada da API
→ tipo Ninja
→ tipo AuthResponse
===================================================================================

AGORA:
App.tsx
→ controla a tela

authService.ts
→ faz login

ninjaService.ts
→ busca ninjas

types/
→ guarda os contratos TypeScript

api.ts
→ guarda a URL base da API
 */