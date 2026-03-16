import './index.css'
import { useTasks } from './hooks/useTasks'
import TaskForm from './components/TaskForm'
import TaskCard from './components/TaskCard'

function App() {
  const { tasks, loading, error, addTask, doneTask } = useTasks()

  return (
    <div className="app">
      <div className="container">
        <TaskForm onSubmit={addTask} />
        <div className="tasks-panel">
          {loading && <p>Loading...</p>}
          {error && <p className="error">{error}</p>}
          {tasks.map(task => (
            <TaskCard key={task.id} task={task} onDone={doneTask} />
          ))}
        </div>
      </div>
    </div>
  )
}

export default App