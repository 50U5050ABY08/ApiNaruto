import type { Ninja } from '../types/ninja'

interface NinjaListProps {
  ninjas: Ninja[]
  mensagem: string
  onListarNinjas: () => void
}

function NinjaList({
  ninjas,
  mensagem,
  onListarNinjas,
}: NinjaListProps) {
  return (
    <section>
      <h2>Ninjas</h2>

      <button onClick={onListarNinjas}>
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
  )
}

export default NinjaList