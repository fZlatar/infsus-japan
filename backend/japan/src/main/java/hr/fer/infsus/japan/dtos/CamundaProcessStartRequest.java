package hr.fer.infsus.japan.dtos;

public record CamundaProcessStartRequest (
    String email,
    Long lessonId
) {}
