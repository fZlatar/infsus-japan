package hr.fer.infsus.japan.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CheckTestResultDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("correctAnswers", 3);
        execution.setVariable("passed", true);
    }
}



// pravi kod gori samo dummy dok se ne implementira jer baca error
// package hr.fer.infsus.japan.delegate;

// import org.camunda.bpm.engine.delegate.DelegateExecution;
// import org.camunda.bpm.engine.delegate.JavaDelegate;
// import org.springframework.stereotype.Component;

// @Component
// public class CheckTestResultDelegate implements JavaDelegate {

//     @Override
//     public void execute(DelegateExecution execution) throws Exception {
//         // Pretpostavljamo da imaš varijablu correctAnswers (broj točnih odgovora)
//         Integer correctAnswers = (Integer) execution.getVariable("correctAnswers");
//         boolean passed = correctAnswers != null && correctAnswers > 2;
//         execution.setVariable("passed", passed);
//     }
// }
