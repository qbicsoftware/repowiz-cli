package life.qbic.repowiz.observer

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class UserAnswer{
    // the user answer is observable, if a state change is triggered all registered listeners are informed!

    String answer
    PropertyChangeSupport support

    UserAnswer(){
        support = new PropertyChangeSupport(this)
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl)
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl)
    }

    public void setAnswer(AnswerTypes propertyName, String value) {
        support.firePropertyChange(propertyName.label, this.answer, value)
        this.answer = value
    }
}
