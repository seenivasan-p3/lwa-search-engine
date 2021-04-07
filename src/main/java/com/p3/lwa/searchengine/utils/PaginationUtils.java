package com.p3.lwa.searchengine.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaginationUtils {

/*
    public <T> Page<T> getListPaginated(final List<T> categories, final int listSize) {
        return new PageImpl<T>(categories.subList(0, listSize), PageRequest.of(0, listSize), listSize);
    }
*/


    public <T> Page<T> getListPaginated(List<T> list) {
        final int size = list.size();
        return new PageImpl<T>(list.subList(0, size), PageRequest.of(0, size), size);
    }

/*    public <T> Page<T> getListPaginated(Pageable pageable, List<T> list) {
        int start = (int) pageable.getOffset();
        int end = ((start + pageable.getPageSize()) > list.size() ? list.size()
                : (start + pageable.getPageSize()));
        return new PageImpl<T>(list.subList(start, end), pageable, list.size());
    }*/

    public Pageable getDefaultPaging()
    {
    return PageRequest.of(0,20);
    }

    public Pageable getPaging(Pageable pageable)
    {
        return PageRequest.of(1,20);
    }


}
