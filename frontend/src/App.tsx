import { useState } from 'react'
import './App.css'

interface Ninja {
  id: number
  nome: string
  email: string
  idade: number
  missao: string | null
}

function App() {
  const titulo: string = 'API Naruto'

  const [mensagem, setMensagem] = useState<string>(
    'Clique no botão para listar os ninjas.'
  )

  const [ninjas, setNinjas] = useState<Ninja[]>([])

  async function listarNinjas() {
  setMensagem('Buscando ninjas da API...')

  const token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c3VhcmlvMiIsImlhdCI6MTc4MjUxNzY3NSwiZXhwIjoxNzgyNTIxMjc1fQ.6Ny1XJEprsj5NybQbT7eXrztQOTf44wQ5mbZzJIrdX8fUl0m2FdJOQiy25h-8pBRcYRS9Haqo7BoI3Kimnf78g'

  try {
    const resposta = await fetch('http://localhost:8080/ninjas', {
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

  return (
    <main>
      <h1>{titulo}</h1>

      <p>
        Front-end desenvolvido com React e TypeScript.
      </p>

      <button onClick={listarNinjas}>
        Listar ninjas
      </button>

      <p>{mensagem}</p>

      <ul>
        {ninjas.map((ninja) => (
          <li key={ninja.id}>
            {ninja.nome} - {ninja.idade} anos - Missão: {ninja.missao ?? 'Sem missão'}
          </li>
        ))}
      </ul>
    </main>
  )
}

export default App