import { useState, useEffect, useCallback } from 'react'
import { getTasks, createTask, completeTask } from '../services/taskApi'

export const useTasks = () => {
  const [tasks, setTasks] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  const fetchTasks = useCallback(async () => {
    try {
      setLoading(true)
      const data = await getTasks()
      setTasks(data)
      setError(null)
    } catch (err) {
      setError('Failed to load tasks')
    } finally {
      setLoading(false)
    }
  }, [])

  useEffect(() => {
    fetchTasks()
  }, [fetchTasks])

  const addTask = async (title, description) => {
    try {
      await createTask(title, description)
      await fetchTasks() // ← fetch fresh list after adding
    } catch (err) {
      setError('Failed to create task')
    }
  }

  const doneTask = async (id) => {
    try {
      await completeTask(id)
      await fetchTasks() // ← fetch fresh list after completing
    } catch (err) {
      setError('Failed to complete task')
    }
  }

  return { tasks, loading, error, addTask, doneTask }
}