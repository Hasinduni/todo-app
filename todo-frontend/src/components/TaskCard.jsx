import { useState } from 'react'

function TaskCard({ task, onDone }) {
  const [completing, setCompleting] = useState(false)

  const handleDone = async () => {
    setCompleting(true)
    await onDone(task.id)
  }

  return (
    <div className={`task-card ${completing ? 'completing' : ''}`}>
      <div className="task-info">
        <h3>{task.title}</h3>
        <p>{task.description}</p>
      </div>
      <button
        className="done-btn"
        onClick={handleDone}
        disabled={completing}
      >
        {completing ? '✓' : 'Done'}
      </button>
    </div>
  )
}

export default TaskCard