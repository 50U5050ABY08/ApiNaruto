import { useEffect, useState } from 'react'
import type { Missao } from '../types/missao'
import type { Ninja, NinjaRequest } from '../types/ninja'

interface NinjaFormProps {
  missoes: Missao[]
  ninjaEmEdicao: Ninja | null
  isAuthenticated: boolean
  isLoading: boolean
  onCriarNinja: (ninjaRequest: NinjaRequest) => void
  onAtualizarNinja: (ninjaRequest: NinjaRequest) => void
  onCancelarEdicao: () => void
}

function NinjaForm({
  missoes,
  ninjaEmEdicao,
  isAuthenticated,
  isLoading,
  onCriarNinja,
  onAtualizarNinja,
  onCancelarEdicao,
}: NinjaFormProps) {
  const [nome, setNome] = useState<string>('')
  const [email, setEmail] = useState<string>('')
  const [idade, setIdade] = useState<string>('')
  const [missaoId, setMissaoId] = useState<string>('')

  const estaEditando = ninjaEmEdicao !== null

  useEffect(() => {
    if (!ninjaEmEdicao) {
      return
    }

    setNome(ninjaEmEdicao.nome)
    setEmail(ninjaEmEdicao.email)
    setIdade(String(ninjaEmEdicao.idade))

    const missaoEncontrada = missoes.find(
      (missao) => missao.missao === ninjaEmEdicao.missao,
    )

    setMissaoId(
      missaoEncontrada ? String(missaoEncontrada.id) : '',
    )
  }, [ninjaEmEdicao, missoes])

  function limparFormulario() {
    setNome('')
    setEmail('')
    setIdade('')
    setMissaoId('')
  }

  function enviarFormulario() {
    if (!nome || !email || !idade || !missaoId) {
      alert('Preencha todos os campos do ninja.')
      return
    }

    const ninjaRequest: NinjaRequest = {
      nome,
      email,
      idade: Number(idade),
      missaoId: Number(missaoId),
    }

    if (estaEditando) {
      onAtualizarNinja(ninjaRequest)
    } else {
      onCriarNinja(ninjaRequest)
    }

    limparFormulario()
  }

  function cancelarEdicao() {
    limparFormulario()
    onCancelarEdicao()
  }

  return (
    <section className="card">
      <div className="card-header">
        <h2>
          {estaEditando ? 'Editar ninja' : 'Cadastrar ninja'}
        </h2>
      </div>

      <div className="form-group">
        <label htmlFor="nome">Nome</label>

        <input
          id="nome"
          type="text"
          placeholder="Nome do ninja"
          value={nome}
          disabled={!isAuthenticated || isLoading}
          onChange={(event) => setNome(event.target.value)}
        />
      </div>

      <div className="form-group">
        <label htmlFor="email">E-mail</label>

        <input
          id="email"
          type="email"
          placeholder="email@konoha.com"
          value={email}
          disabled={!isAuthenticated || isLoading}
          onChange={(event) => setEmail(event.target.value)}
        />
      </div>

      <div className="form-group">
        <label htmlFor="idade">Idade</label>

        <input
          id="idade"
          type="number"
          placeholder="Idade"
          value={idade}
          disabled={!isAuthenticated || isLoading}
          onChange={(event) => setIdade(event.target.value)}
        />
      </div>

      <div className="form-group">
        <label htmlFor="missao">Missão</label>

        <select
          id="missao"
          value={missaoId}
          disabled={!isAuthenticated || isLoading}
          onChange={(event) => setMissaoId(event.target.value)}
        >
          <option value="">Selecione uma missão</option>

          {missoes.map((missao) => (
            <option key={missao.id} value={missao.id}>
              {missao.missao} - Rank {missao.rankingDaMissao}
            </option>
          ))}
        </select>
      </div>

      <div className="button-group">
        <button
          className="button button-primary"
          onClick={enviarFormulario}
          disabled={!isAuthenticated || isLoading}
        >
          {estaEditando ? 'Atualizar ninja' : 'Cadastrar ninja'}
        </button>

        {estaEditando && (
          <button
            className="button button-secondary"
            onClick={cancelarEdicao}
            disabled={isLoading}
          >
            Cancelar
          </button>
        )}
      </div>
    </section>
  )
}

export default NinjaForm