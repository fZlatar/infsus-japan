package hr.fer.infsus.japan.services;

public interface JwtService {

    String generateAccess(String email);

    String generateRefresh(String email);

    String extractSubjectFromAccess(String jwt);

    String extractSubjectFromRefresh(String jwt);

}
