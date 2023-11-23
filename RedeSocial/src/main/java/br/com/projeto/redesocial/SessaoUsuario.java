package br.com.projeto.redesocial;

import br.com.projeto.redesocial.backend.Usuario;

public class SessaoUsuario {
    private static Usuario usuarioLogado;

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuario) {
        SessaoUsuario.usuarioLogado = usuario;
    }
}
