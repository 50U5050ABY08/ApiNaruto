import { useState } from 'react'
import './App.css'
import LoginForm from './components/LoginForm'
import NinjaForm from './components/NinjaForm'
import NinjaList from './components/NinjaList'
import { login } from './services/authService'
import { buscarMissoes } from './services/missaoService'
import { atualizarNinja, buscarNinjas, criarNinja, deletarNinja } from './services/ninjaService'
import type { Missao } from './types/missao'
import type { Ninja, NinjaRequest } from './types/ninja'


function App() {
  const [missoes, setMissoes] = useState<Missao[]>([])
  
  const titulo: string = 'API Naruto'

  const [username, setUsername] = useState<string>('')
  const [password, setPassword] = useState<string>('')

  const [token, setToken] = useState<string>('')
  const [role, setRole] = useState<string>('')
  const [mensagem, setMensagem] = useState<string>(
    'Faça login para acessar os ninjas.',
  )

  const [ninjas, setNinjas] = useState<Ninja[]>([])
  const [ninjaEmEdicao, setNinjaEmEdicao] = useState<Ninja | null>(null)
  const [isLoading, setIsLoading] = useState<boolean>(false)

  const isAuthenticated = token.length > 0
  const isAdmin = role === 'ROLE_ADMIN'

  function editarNinja(ninja: Ninja) {
  setNinjaEmEdicao(ninja)
  setMensagem(`Editando ninja: ${ninja.nome}`)
}

function cancelarEdicao() {
  setNinjaEmEdicao(null)
  setMensagem('Edição cancelada.')
}

async function atualizarNinjaSelecionado(
  ninjaRequest: NinjaRequest,
) {
  if (!token || !ninjaEmEdicao) {
    setMensagem('Você precisa selecionar um ninja para atualizar.')
    return
  }

  setMensagem('Atualizando ninja...')
  setIsLoading(true)

  try {
    const ninjaAtualizado = await atualizarNinja(
      token,
      ninjaEmEdicao.id,
      ninjaRequest,
    )

    setNinjas((ninjasAtuais) =>
      ninjasAtuais.map((ninja) =>
        ninja.id === ninjaAtualizado.id
          ? ninjaAtualizado
          : ninja,
      ),
    )

    setNinjaEmEdicao(null)
    setMensagem('Ninja atualizado com sucesso.')
  } catch (error) {
    console.error(error)

    if (error instanceof Error) {
      setMensagem(error.message)
    } else {
      setMensagem('Erro ao atualizar ninja.')
    }
  } finally {
    setIsLoading(false)
  }
}
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
setRole(response.role)
setPassword('')
setNinjas([])

const missoesEncontradas = await buscarMissoes(response.token)
setMissoes(missoesEncontradas)

setMensagem('Login realizado com sucesso.')
    } catch (error) {
      console.error(error)

      setToken('')
      setRole('')
      setNinjas([])
      setMissoes([])

      if (error instanceof Error) {
        setMensagem(error.message)
      } else {
        setMensagem('Erro ao realizar login.')
      }
   }finally {
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
      setRole('')
      setNinjas([])
      setMissoes([])

      if (error instanceof Error) {
        setMensagem(error.message)
      } else {
        setMensagem('Erro ao buscar ninjas.')
      }
    } finally {
      setIsLoading(false)
    }
  }

  function sair() {
    setToken('')
    setRole('')
    setNinjas([])
    setMissoes([])
    setNinjaEmEdicao(null)
    setUsername('')
    setPassword('')
    setMensagem('Você saiu da aplicação.')
  }

  async function cadastrarNinja(ninjaRequest: NinjaRequest) {
  if (!token) {
    setMensagem('Você precisa fazer login antes de cadastrar um ninja.')
    return
  }

  setMensagem('Cadastrando ninja...')
  setIsLoading(true)

  try {
    const ninjaCriado = await criarNinja(token, ninjaRequest)

    setNinjas((ninjasAtuais) => [
      ...ninjasAtuais,
      ninjaCriado,
    ])

    setMensagem('Ninja cadastrado com sucesso.')
  } catch (error) {
    console.error(error)

    if (error instanceof Error) {
      setMensagem(error.message)
    } else {
      setMensagem('Erro ao cadastrar ninja.')
    }
  } finally {
    setIsLoading(false)
  }
}

async function excluirNinja(ninjaId: number) {
  if (!token) {
    setMensagem('Você precisa fazer login antes de excluir um ninja.')
    return
  }

  const confirmou = window.confirm(
    'Tem certeza que deseja excluir este ninja?',
  )

  if (!confirmou) {
    return
  }

  setMensagem('Excluindo ninja...')
  setIsLoading(true)

  try {
    await deletarNinja(token, ninjaId)

    setNinjas((ninjasAtuais) =>
      ninjasAtuais.filter((ninja) => ninja.id !== ninjaId),
    )

    if (ninjaEmEdicao?.id === ninjaId) {
      setNinjaEmEdicao(null)
    }

    setMensagem('Ninja excluído com sucesso.')
  } catch (error) {
    console.error(error)

    if (error instanceof Error) {
      setMensagem(error.message)
    } else {
      setMensagem('Erro ao excluir ninja.')
    }
  } finally {
    setIsLoading(false)
  }
}

  return (
  <main className="app-container">
    <header className="app-header">
      <h1>{titulo}</h1>

      <p>
        Front-end desenvolvido com React e TypeScript.
      </p>
    </header>

    <div className="app-content">
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

   <NinjaForm
  missoes={missoes}
  ninjaEmEdicao={ninjaEmEdicao}
  isAuthenticated={isAuthenticated}
  isLoading={isLoading}
  onCriarNinja={cadastrarNinja}
  onAtualizarNinja={atualizarNinjaSelecionado}
  onCancelarEdicao={cancelarEdicao}
/>

      <NinjaList
  ninjas={ninjas}
  mensagem={mensagem}
  isAuthenticated={isAuthenticated}
  isAdmin={isAdmin}
  isLoading={isLoading}
  onListarNinjas={listarNinjas}
  onEditarNinja={editarNinja}
  onExcluirNinja={excluirNinja}
/>
    </div>
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

=====================================================================================

Regra pra guardar:

try {
  // tenta fazer a requisição
} catch (error) {
  // trata o erro
} finally {
  // sempre executa, dando certo ou dando erro
}

tradução:
try
→ tente

catch
→ capture o erro

finally
→ finalmente / sempre execute
 */