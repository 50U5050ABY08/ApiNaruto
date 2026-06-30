import { describe, expect, it, vi } from 'vitest'
import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import NinjaForm from './NinjaForm'
import type { Ninja } from '../types/ninja'
import type { Missao } from '../types/missao'

const missoes: Missao[] = [
{
    id: 2,
    missao: 'Exame Chunin',
    rankingDaMissao: 'B',
},
{
    id: 1,
    missao: 'Proteger a Ponte Atualizada',
    rankingDaMissao: 'A',
},
]


const ninjaEmEdicao: Ninja = {
    id: 10,
    nome: 'Naruto Uzumaki',
    email: 'naruto@folha.com',
    idade: 16,
    missao: 'Proteger a Ponte Atualizada',
}


describe('NinjaForm', () => {
    it('deve desabilitar formulário quando usuário não estiver autenticado', () => {
        render(
            <NinjaForm
                missoes={missoes}
                ninjaEmEdicao={null}
                isAuthenticated={false}
                isLoading={false}
                onCriarNinja={vi.fn()}
                onAtualizarNinja={vi.fn()}
                onCancelarEdicao={vi.fn()}
              />,
            )
            
            
            expect(screen.getByLabelText('Nome')).toBeDisabled()
            expect(screen.getByLabelText('E-mail')).toBeDisabled()
            expect(screen.getByLabelText('Idade')).toBeDisabled()
            expect(screen.getByLabelText('Missão')).toBeDisabled()


            expect(
                screen.getByRole('button', { name: 'Cadastrar ninja' }),
            ).toBeDisabled()
        })

        it('deve mostrar as missões no select', () => {
            render(
                <NinjaForm
                missoes={missoes}
                ninjaEmEdicao={null}
                isAuthenticated={true}
                isLoading={false}
                onCriarNinja={vi.fn()}
                onAtualizarNinja={vi.fn()}
                onCancelarEdicao={vi.fn()}
                />,
            )

            expect(
                screen.getByRole('option', {
                    name: 'Exame Chunin - Rank B',
                }),
            ).toBeInTheDocument()

            expect(
                screen.getByRole('option', {
                    name: 'Proteger a Ponte Atualizada - Rank A',
                }),
            ).toBeInTheDocument()
        })

        it('deve chamar onCriarNinja ao preencher e cadastrar', async () => {
            const user = userEvent.setup()
            const onCriarNinja = vi.fn()


            render(
                <NinjaForm
                missoes={missoes}
                ninjaEmEdicao={null}
                isAuthenticated={true}
                isLoading={false}
                onCriarNinja={onCriarNinja}
                onAtualizarNinja={vi.fn()}
                onCancelarEdicao={vi.fn()}
              />,
            )
            
            
            await user.type(screen.getByLabelText('Nome'), 'Hinata Hyuga')
            await user.type(screen.getByLabelText('E-mail'), 'hinata@konoha.com')
            await user.type(screen.getByLabelText('Idade'), '18')
            await user.selectOptions(screen.getByLabelText('Missão'), '2')


            await user.click(
                screen.getByRole('button', { name: 'Cadastrar ninja' }),
            )

            expect(onCriarNinja).toHaveBeenCalledWith({
                nome: 'Hinata Hyuga',
                email: 'hinata@konoha.com',
                idade: 18,
                missaoId: 2,
            })
        })

        it('deve preencher formulário ao editar ninja', () => {
            render(
                <NinjaForm
                    missoes={missoes}
                    ninjaEmEdicao={ninjaEmEdicao}
                    isAuthenticated={true}
                    isLoading={false}
                    onCriarNinja={vi.fn()}
                    onAtualizarNinja={vi.fn()}
                    onCancelarEdicao={vi.fn()}
                  />,  
                )


                expect(screen.getByText('Editar ninja')).toBeInTheDocument()
                expect(screen.getByDisplayValue('Naruto Uzumaki')).toBeInTheDocument()
                expect(screen.getByDisplayValue('naruto@folha.com')).toBeInTheDocument()
                expect(screen.getByDisplayValue('16')).toBeInTheDocument()
                expect(screen.getByLabelText('Missão')).toHaveValue('1')
        })


        it('deve chamar onAtualizadaNinja ao editar e salvar', async () => {
            const user = userEvent.setup()
            const onAtualizarNinja = vi.fn()

            render(
                <NinjaForm
                missoes={missoes}
                ninjaEmEdicao={ninjaEmEdicao}
                isAuthenticated={true}
                isLoading={false}
                onCriarNinja={vi.fn()}
                onAtualizarNinja={onAtualizarNinja}
                onCancelarEdicao={vi.fn()}
               />,  
            )


            const nomeInput = screen.getByLabelText('Nome')

            await user.clear(nomeInput)
            await user.type(nomeInput, 'Naruto Atualizado')


            await user.click(
                screen.getByRole('button', {name: 'Atualizar ninja' }),
            )


            expect(onAtualizarNinja).toHaveBeenCalledWith({
                nome: 'Naruto Atualizado',
                email: 'naruto@folha.com',
                idade: 16,
                missaoId: 1,
            })
        })


        it('deve chamar onCancelarEdicao ao clicar em Cancelar', async () => {
            const user = userEvent.setup()
            const onCancelarEdicao = vi.fn()


            render(
                <NinjaForm
                    missoes={missoes}
                    ninjaEmEdicao={ninjaEmEdicao}
                    isAuthenticated={true}
                    isLoading={false}
                    onCriarNinja={vi.fn()}
                    onAtualizarNinja={vi.fn()}
                    onCancelarEdicao={onCancelarEdicao}
                   />, 
            )

            await user.click(
                screen.getByRole('button', { name: 'Cancelar' }),
            )


            expect(onCancelarEdicao).toHaveBeenCalledTimes(1)
          })
        })