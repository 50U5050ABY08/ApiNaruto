import type { Ninja } from '../types/ninja'

interface NinjaListProps {
  ninjas: Ninja[]
  mensagem: string
  isAuthenticated: boolean
  isAdmin: boolean
  isLoading: boolean
  onListarNinjas: () => void
  onEditarNinja: (ninja: Ninja) => void
  onExcluirNinja: (ninjaId: number) => void
}

function NinjaList({
  ninjas,
  mensagem,
  isAuthenticated,
  isAdmin,
  isLoading,
  onListarNinjas,
  onEditarNinja,
  onExcluirNinja,
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
                <th>Ações</th>
              </tr>
            </thead>

            <tbody>
              {ninjas.map((ninja) => (
                <tr key={ninja.id}>
                  <td>{ninja.nome}</td>
                  <td>{ninja.email}</td>
                  <td>{ninja.idade}</td>
                  <td>{ninja.missao ?? 'Sem missão'}</td>
                  <td>
                    <div className="table-actions">
                      <button
                        className="button button-secondary"
                        onClick={() => onEditarNinja(ninja)}
                        disabled={isLoading}
                      >
                        Editar
                      </button>

                      {isAdmin && (
                        <button
                          className="button button-danger"
                          onClick={() => onExcluirNinja(ninja.id)}
                          disabled={isLoading}
                        >
                          Excluir
                        </button>
                      )}
                    </div>
                  </td>
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