import { render, screen, fireEvent } from '@testing-library/react'
import { describe, it, expect, vi } from 'vitest'
import TaskCard from '../components/TaskCard'

const mockTask = {
  id: 1,
  title: 'Buy books',
  description: 'Buy for school',
  completed: false,
}

describe('TaskCard', () => {
  it('renders task title and description', () => {
    render(<TaskCard task={mockTask} onDone={() => {}} />)
    expect(screen.getByText('Buy books')).toBeInTheDocument()
    expect(screen.getByText('Buy for school')).toBeInTheDocument()
  })

  it('renders Done button', () => {
    render(<TaskCard task={mockTask} onDone={() => {}} />)
    expect(screen.getByText('Done')).toBeInTheDocument()
  })

  it('calls onDone with task id when Done is clicked', async () => {
    const onDone = vi.fn().mockResolvedValue()
    render(<TaskCard task={mockTask} onDone={onDone} />)
    fireEvent.click(screen.getByText('Done'))
    expect(onDone).toHaveBeenCalledWith(1)
  })

  it('disables Done button after clicking', async () => {
    const onDone = vi.fn().mockResolvedValue()
    render(<TaskCard task={mockTask} onDone={onDone} />)
    fireEvent.click(screen.getByText('Done'))
    expect(screen.getByRole('button')).toBeDisabled()
  })
})