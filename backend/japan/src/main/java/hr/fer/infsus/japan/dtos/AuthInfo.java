package hr.fer.infsus.japan.dtos;

public record AuthInfo(
        String access,
        String refresh,
        String email
) {
}
