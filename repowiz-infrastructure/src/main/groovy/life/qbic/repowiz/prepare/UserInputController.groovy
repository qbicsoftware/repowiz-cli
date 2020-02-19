package life.qbic.repowiz.prepare

class UserInputController {

    UserAnswer userAnswer

    UserInputController(UserAnswer answer){
        userAnswer = answer
    }

    def transferUserAnswer(String answer){
        userAnswer.handleUserAnswer(answer)
    }
}
