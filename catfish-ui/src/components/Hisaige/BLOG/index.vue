<template>
  <div>
    <svg-icon icon-class="blog" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'HisaigeBlog',
  data() {
    return {
      url: 'https://www.baidu.com'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
