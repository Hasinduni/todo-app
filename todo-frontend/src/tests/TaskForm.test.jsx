import { render, screen, fireEvent, waitFor } from '@testing-library/react'
import { describe, it, expect, vi } from 'vitest'
import TaskForm from '../components/TaskForm'

describe('TaskForm', () => {
  it('renders title and description inputs', () => {
    render(<TaskForm onSubmit={() => {}} />)
    expect(screen.getByPlaceholderText('Enter task title')).toBeInTheDocument()
    expect(screen.getByPlaceholderText('Enter task description')).toBeInTheDocument()
  })

  it('renders Add button', () => {
    render(<TaskForm onSubmit={() => {}} />)
    expect(screen.getByText('Add')).toBeInTheDocument()
  })

  it('calls onSubmit with title and description', async () => {
    const onSubmit = vi.fn().mockResolvedValue()
    render(<TaskForm onSubmit={onSubmit} />)

    fireEvent.change(screen.getByPlaceholderText('Enter task title'), {
      target: { value: 'Buy books' },
    })
    fireEvent.change(screen.getByPlaceholderText('Enter task description'), {
      target: { value: 'Buy for school' },
    })
    fireEvent.click(screen.getByText('Add'))

    await waitFor(() => {
      expect(onSubmit).toHaveBeenCalledWith('Buy books', 'Buy for school')
    })
  })

  it('does not submit when title is empty', async () => {
    const onSubmit = vi.fn()
    render(<TaskForm onSubmit={onSubmit} />)

    fireEvent.change(screen.getByPlaceholderText('Enter task description'), {
      target: { value: 'Buy for school' },
    })
    fireEvent.click(screen.getByText('Add'))

    await waitFor(() => {
      expect(onSubmit).not.toHaveBeenCalled()
    })
  })

  it('does not submit when description is empty', async () => {
    const onSubmit = vi.fn()
    render(<TaskForm onSubmit={onSubmit} />)

    fireEvent.change(screen.getByPlaceholderText('Enter task title'), {
      target: { value: 'Buy books' },
    })
    fireEvent.click(screen.getByText('Add'))

    await waitFor(() => {
      expect(onSubmit).not.toHaveBeenCalled()
    })
  })

  it('clears inputs after successful submit', async () => {
    const onSubmit = vi.fn().mockResolvedValue()
    render(<TaskForm onSubmit={onSubmit} />)

    fireEvent.change(screen.getByPlaceholderText('Enter task title'), {
      target: { value: 'Buy books' },
    })
    fireEvent.change(screen.getByPlaceholderText('Enter task description'), {
      target: { value: 'Buy for school' },
    })
    fireEvent.click(screen.getByText('Add'))

    await waitFor(() => {
      expect(screen.getByPlaceholderText('Enter task title').value).toBe('')
      expect(screen.getByPlaceholderText('Enter task description').value).toBe('')
    })
  })
})