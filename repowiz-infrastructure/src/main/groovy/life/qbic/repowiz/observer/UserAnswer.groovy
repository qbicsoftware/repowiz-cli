package life.qbic.repowiz.observer

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

/**
 * This class defines how a UserAnswer can be defined allows to add a listener to track changes
 *
 * The user answer is observable, if a state change is triggered all registered listeners are informed!
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class UserAnswer {

    private String answer
    private PropertyChangeSupport support

    /**
     * Creates a new UserAnswer by creating PropertyChangeSupport instance
     */
    UserAnswer() {
        support = new PropertyChangeSupport(this)
    }

    /**
     * Adds a {@link PropertyChangeListener} to the {@link PropertyChangeSupport}
     * @param pcl
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl)
    }

    /**
     * Removes a specific {@link PropertyChangeListener} from the {@link PropertyChangeSupport}
     * @param pcl as a specific change listener that should be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl)
    }

    /**
     * Sets the answer for this class
     * @param propertyName assigns the answer type of the answer
     * @param value sets the value of the answer
     */
    public void setAnswer(AnswerTypes propertyName, String value) {
        support.firePropertyChange(propertyName.label, this.answer, value)
        this.answer = value
    }
}
