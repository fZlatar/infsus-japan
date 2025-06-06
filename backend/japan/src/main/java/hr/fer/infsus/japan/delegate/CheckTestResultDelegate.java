package hr.fer.infsus.japan.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CheckTestResultDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Object correctAnswerObj = execution.getVariable("correctAnswers");
        if (correctAnswerObj == null || !(correctAnswerObj instanceof Integer)) {
            throw new IllegalArgumentException("Varijabla correctAnswers nije postavljna ili nije broj");
        }
        Integer correctAnswers = (Integer) correctAnswerObj;
        boolean passed = correctAnswers > 2;
        execution.setVariable("passed", passed);
    }
}
