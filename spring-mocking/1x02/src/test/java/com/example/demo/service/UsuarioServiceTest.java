package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UsuarioService usuarioService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void deveRetornarUsuarioQuandoIdExistir() {
		// Arrange
		Usuario usuario = new Usuario(1L, "Carlos", "carlos@email.com");

		Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

		// Act
		Usuario resultado = usuarioService.buscarUsuarioPorId(1L);

		// Assert
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
		assertEquals("Carlos", resultado.getNome());
		assertEquals("carlos@email.com", resultado.getEmail());
	}

	@Test
	void deveLancarExcecaoQuandoUsuarioNaoExistir() {
		// Arrange
		Mockito.when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

		// Act + Assert
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			usuarioService.buscarUsuarioPorId(99L);
		});

		assertEquals("Usuário não encontrado", exception.getMessage());
	}

	@Test
	void deveSalvarUsuarioComSucesso() {
		Usuario usuario = new Usuario(1L, "Ana", "ana@email.com");

		Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario);

		Usuario resultado = usuarioService.salvarUsuario(usuario);

		assertNotNull(resultado);
		assertEquals("Ana", resultado.getNome());
		assertEquals("ana@email.com", resultado.getEmail());
	}
}
