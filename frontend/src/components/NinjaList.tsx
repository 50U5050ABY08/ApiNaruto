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
  <section className="card">
    <div className="card-header">
      <h2>Ninjas</h2>
    </div>

    <button
      className="button button-primary"
      onClick={onListarNinjas}
      disabled={!isAuthenticated || isLoading}
    >
      Listar ninjas
    </button>

    <p className="message">{mensagem}</p>

    {isAuthenticated && ninjas.length > 0 && (
      <div className="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Nome</th>
              <th>E-mail</th>
              <th>Idade</th>
              <th>Missão</th>
            </tr>
          </thead>

          <tbody>
            {ninjas.map((ninja) => (
              <tr key={ninja.id}>
                <td>{ninja.nome}</td>
                <td>{ninja.email}</td>
                <td>{ninja.idade}</td>
                <td>{ninja.missao ?? 'Sem missão'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    )}
  </section>
)
}

export default NinjaList