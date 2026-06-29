import type { Ninja } from '../types/ninja'

interface NinjaListProps {
  ninjas: Ninja[]
  mensagem: string
  isAuthenticated: boolean
  isLoading: boolean
  onListarNinjas: () => void
}

function NinjaList({
  ninjas,
  mensagem,
  isAuthenticated,
  isLoading,
  onListarNinjas,
}: NinjaListProps) {
  return (
    <section>
      <h2>Ninjas</h2>

      <button
        onClick={onListarNinjas}
        disabled={!isAuthenticated || isLoading}
      >
        Listar ninjas
      </button>

      <p>{mensagem}</p>

      {isAuthenticated && ninjas.length > 0 && (
        <ul>
          {ninjas.map((ninja) => (
            <li key={ninja.id}>
              {ninja.nome} - {ninja.idade} anos - Missão:{' '}
              {ninja.missao ?? 'Sem missão'}
            </li>
          ))}
        </ul>
      )}
    </section>
  )
}

export default NinjaList