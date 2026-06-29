import { useState } from 'react'
import type { Missao } from '../types/missao'
import type { NinjaRequest } from '../types/ninja'

interface NinjaFormProps {
  missoes: Missao[]
  isAuthenticated: boolean
  isLoading: boolean
  onCriarNinja: (ninjaRequest: NinjaRequest) => void
}

function NinjaForm({
  missoes,
  isAuthenticated,
  isLoading,
  onCriarNinja,
}: NinjaFormProps) {
  const [nome, setNome] = useState<string>('')
  const [email, setEmail] = useState<string>('')
  const [idade, setIdade] = useState<string>('')
  const [missaoId, setMissaoId] = useState<string>('')

  function enviarFormulario() {
    if (!nome || !email || !idade || !missaoId) {
      alert('Preencha todos os campos do ninja.')
      return
    }

    onCriarNinja({
      nome,
      email,
      idade: Number(idade),
      missaoId: Number(missaoId),
    })

    setNome('')
    setEmail('')
    setIdade('')
    setMissaoId('')
  }

  return (
    <section className="card">
      <div className="card-header">
        <h2>Cadastrar ninja</h2>
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

      <button
        className="button button-primary"
        onClick={enviarFormulario}
        disabled={!isAuthenticated || isLoading}
      >
        Cadastrar ninja
      </button>
    </section>
  )
}

export default NinjaForm