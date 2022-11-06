package com.epam.esm.hateoas.assemblers;

import com.epam.esm.Tag;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class TagModelAssembler extends RepresentationModelAssemblerSupport<Tag, TagDto> {
    public TagModelAssembler() {
        super(TagController.class, TagDto.class);
    }

    @Override
    public TagDto toModel(Tag entity) {
        TagDto model = new TagDto();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}
