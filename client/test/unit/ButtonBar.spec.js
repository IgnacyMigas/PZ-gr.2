import { shallowMount } from '@vue/test-utils'
import ButtonBar from '@/components/elements/ButtonBar'

describe('ButtonBar', () => {
  let counter = 0

  const btn = shallowMount(ButtonBar, {
    propsData: {
      icon: 'exit',
      handler: () => counter += 1
    }
  })

  it('handler function called when clicking', () => {
    const last_counter_val = counter
    btn.trigger('click')
    expect(counter).toBe(last_counter_val + 1)
  })
})
