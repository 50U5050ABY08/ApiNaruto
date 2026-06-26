/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev.breno.ApiNaruto.service;

import dev.breno.ApiNaruto.dto.NinjaRequestDTO;
import dev.breno.ApiNaruto.dto.NinjaResponseDTO;
import dev.breno.ApiNaruto.exception.RegraDeNegocioException;
import dev.breno.ApiNaruto.model.MissaoModel;
import dev.breno.ApiNaruto.model.NinjaModel;
import dev.breno.ApiNaruto.repository.MissaoRepository;
import dev.breno.ApiNaruto.repository.NinjaRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NinjaServiceTest {

    @Mock
    private NinjaRepository ninjaRepository;

    @Mock
    private MissaoRepository missaoRepository;

    @InjectMocks
    private NinjaService ninjaService;

    @Test
    void deveSalvarNinjaValido() {

        NinjaRequestDTO request = criarRequest(
                "Shikamaru Nara",
                "shikamaru@konoha.com",
                20,
                1L
        );

        MissaoModel missao = new MissaoModel();
        missao.setRankingDaMissao("B");

        when(missaoRepository.findById(1L))
                .thenReturn(Optional.of(missao));

        when(ninjaRepository.save(any(NinjaModel.class)))
                .thenAnswer(invocation -> {

                    NinjaModel ninja = invocation.getArgument(0);

                    // Confirma que o cliente não conseguiu definir o ID.
                    assertThat(ninja.getId()).isNull();

                    ninja.setId(10L);

                    return ninja;
                });

        NinjaResponseDTO response =
                ninjaService.salvarNinja(request);

        assertThat(response.getId()).isEqualTo(10L);
        assertThat(response.getNome()).isEqualTo("Shikamaru Nara");
        assertThat(response.getEmail()).isEqualTo("shikamaru@konoha.com");
        assertThat(response.getIdade()).isEqualTo(20);

        verify(missaoRepository).findById(1L);
        verify(ninjaRepository).save(any(NinjaModel.class));
    }

    @Test
    void deveImpedirMenorDeIdadeEmMissaoRankA() {

        NinjaRequestDTO request = criarRequest(
                "Konohamaru",
                "konohamaru@konoha.com",
                12,
                1L
        );

        MissaoModel missao = new MissaoModel();
        missao.setRankingDaMissao("A");

        when(missaoRepository.findById(1L))
                .thenReturn(Optional.of(missao));

        assertThatThrownBy(
                () -> ninjaService.salvarNinja(request)
        )
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessageContaining(
                        "menores de 18 anos"
                );

        verify(ninjaRepository, never())
                .save(any(NinjaModel.class));
    }

    @Test
    void deveLancar404QuandoMissaoNaoExistir() {

        NinjaRequestDTO request = criarRequest(
                "Neji Hyuga",
                "neji@konoha.com",
                18,
                999L
        );

        when(missaoRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> ninjaService.salvarNinja(request)
        )
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(exception -> {

                    ResponseStatusException responseException =
                            (ResponseStatusException) exception;

                    assertThat(responseException.getStatusCode())
                            .isEqualTo(HttpStatus.NOT_FOUND);
                });

        verify(ninjaRepository, never())
                .save(any(NinjaModel.class));
    }

    @Test
    void deveDeletarNinjaExistente() {

        NinjaModel ninja = new NinjaModel();
        ninja.setId(5L);
        ninja.setNome("Gaara");

        when(ninjaRepository.findById(5L))
                .thenReturn(Optional.of(ninja));

        ninjaService.deletarNinja(5L);

        verify(ninjaRepository).findById(5L);
        verify(ninjaRepository).delete(ninja);
    }

    @Test
    void deveLancar404AoDeletarNinjaInexistente() {

        when(ninjaRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> ninjaService.deletarNinja(999L)
        )
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(exception -> {

                    ResponseStatusException responseException =
                            (ResponseStatusException) exception;

                    assertThat(responseException.getStatusCode())
                            .isEqualTo(HttpStatus.NOT_FOUND);
                });

        verify(ninjaRepository, never())
                .delete(any(NinjaModel.class));
    }

    private NinjaRequestDTO criarRequest(
            String nome,
            String email,
            int idade,
            Long missaoId) {

        NinjaRequestDTO request = new NinjaRequestDTO();

        request.setNome(nome);
        request.setEmail(email);
        request.setIdade(idade);
        request.setMissaoId(missaoId);

        return request;
    }
    
    @Test
    void deveImpedirAtualizacaoDeMenorParaMissaoRankA() {

        NinjaModel ninjaExistente = new NinjaModel();
        ninjaExistente.setId(1L);
        ninjaExistente.setNome("Konohamaru");
        ninjaExistente.setEmail("konohamaru@konoha.com");
        ninjaExistente.setIdade(12);

        NinjaRequestDTO request = criarRequest(
                "Konohamaru",
                "konohamaru@konoha.com",
                12,
                10L
        );

        MissaoModel missaoRankA = new MissaoModel();
        missaoRankA.setRankingDaMissao("A");

        when(ninjaRepository.findById(1L))
                .thenReturn(Optional.of(ninjaExistente));

        when(missaoRepository.findById(10L))
                .thenReturn(Optional.of(missaoRankA));

        assertThatThrownBy(
                () -> ninjaService.atualizarNinja(1L, request)
        )
                .isInstanceOf(RegraDeNegocioException.class)
                .hasMessageContaining("menores de 18 anos");

        verify(ninjaRepository, never())
                .save(any(NinjaModel.class));
        }
}