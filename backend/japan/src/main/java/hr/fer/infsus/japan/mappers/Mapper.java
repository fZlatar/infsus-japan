package hr.fer.infsus.japan.mappers;

public interface Mapper<E, D> {

    D toDto(E e);

    E toEntity(D dto);

}
