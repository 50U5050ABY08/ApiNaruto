import { useState } from 'react'
import './App.css'
import LoginForm from './components/LoginForm'
import NinjaList from './components/NinjaList'
import { login } from './services/authService'
import { buscarNinjas } from './services/ninjaService'
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
  const [isLoading, setIsLoading] = useState<boolean>(false)

  const isAuthenticated = token.length > 0

  async function fazerLogin() {
    if (!username || !password) {
      setMensagem('Informe usuário e senha.')
      return
    }

    setMensagem('Realizando login...')
    setIsLoading(true)

    try {
      const response = await login({
        username,
        password,
      })

      setToken(response.token)
      setPassword('')
      setNinjas([])
      setMensagem('Login realizado com sucesso.')
    } catch (error) {
      console.error(error)

      setToken('')
      setNinjas([])
      setMensagem('Usuário ou senha inválidos.')
    } finally {
      setIsLoading(false)
    }
  }

  async function listarNinjas() {
    if (!token) {
      setMensagem('Você precisa fazer login antes de listar os ninjas.')
      return
    }

    setMensagem('Buscando ninjas da API...')
    setIsLoading(true)

    try {
      const ninjasEncontrados = await buscarNinjas(token)

      setNinjas(ninjasEncontrados)
      setMensagem('Ninjas carregados com sucesso.')
    } catch (error) {
      console.error(error)

      setToken('')
      setNinjas([])
      setMensagem('Erro ao buscar ninjas. Faça login novamente.')
    } finally {
      setIsLoading(false)
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

      <LoginForm
        username={username}
        password={password}
        isAuthenticated={isAuthenticated}
        isLoading={isLoading}
        onUsernameChange={setUsername}
        onPasswordChange={setPassword}
        onLogin={fazerLogin}
        onLogout={sair}
      />

      <NinjaList
        ninjas={ninjas}
        mensagem={mensagem}
        isAuthenticated={isAuthenticated}
        isLoading={isLoading}
        onListarNinjas={listarNinjas}
      />
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
App.tsx
→ tinha toda a tela dentro dele
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

=====================================================================================

App.tsx
→ controla o estado geral

LoginForm.tsx
→ cuida apenas do formulário de login

NinjaList.tsx
→ cuida apenas da listagem dos ninjas
 */