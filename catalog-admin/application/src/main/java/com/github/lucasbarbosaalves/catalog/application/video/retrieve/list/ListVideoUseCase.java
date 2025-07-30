package com.github.lucasbarbosaalves.catalog.application.video.retrieve.list;

import com.github.lucasbarbosaalves.catalog.application.UseCase;
import com.github.lucasbarbosaalves.catalog.domain.pagination.Pagination;
import com.github.lucasbarbosaalves.catalog.domain.video.VideoSearchQuery;

public abstract class ListVideoUseCase extends UseCase<VideoSearchQuery, Pagination<VideoListOutput>> {
}
