package br.com.importando.xlsx.mapper;

import br.com.importando.xlsx.dto.ContatoDto;

public class ContatoMapper {

    public static ContatoDto createContato(String phone, String name) {
        String newPhone = phone.replaceAll("\\p{Punct}", "");
        return ContatoDto.builder()
                .name(name)
                .phone(newPhone)
                .build();
    }
}
