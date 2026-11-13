<template>
  <div class="hello-container">
    <h1>Hello World! 🎉</h1>
    <p>Congratulations, you have logged in successfully!</p>

    <!-- Action buttons container -->
    <div class="button-container">
      <button @click="goBack" class="btn">Return to login page</button>
      <button @click="openCourseApi" class="btn">Open Course API</button>
      <button @click="fetchCourses" class="btn" :disabled="loading">
        {{ loading ? 'Loading...' : 'Load Courses' }}
      </button>
    </div>

    <!-- Improvement (a): heading only shows after the button is clicked -->
    <h2 v-if="hasSearched">Course List</h2>

    <!-- FIX: statusMessage was set in the script but never shown -->
    <p v-if="statusMessage" class="status">{{ statusMessage }}</p>

    <!-- Course Table -->
    <table v-if="courses.length > 0" class="course-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Course Code</th>
          <th>Course Title</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="course in courses" :key="course.id">
          <td>{{ course.id }}</td>
          <td>{{ course.code }}</td>
          <td>{{ course.title }}</td>
        </tr>
      </tbody>
    </table>

    <!-- Empty State -->
    <div v-else-if="hasSearched && !statusMessage">
      <p>No courses found in the database.</p>
    </div>

    <!-- Initial State -->
    <div v-else-if="!hasSearched">
      <p>Click the button to load courses.</p>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ref } from 'vue'

const router = useRouter()

// Reactive state variables
const courses = ref([])
const loading = ref(false)
const statusMessage = ref('')
const hasSearched = ref(false)   // FIX: used in the template but never declared in the lab code

// Navigate back to login
const goBack = () => {
  localStorage.clear()
  router.push('/')
}

// Open the backend course API in a new tab
const openCourseApi = () => {
  window.open('http://localhost:8080/api/courses', '_blank')
}

// Fetch courses asynchronously and store them in reactive state
const fetchCourses = async () => {
  loading.value = true
  statusMessage.value = ''
  hasSearched.value = true

  try {
    const response = await fetch('http://localhost:8080/api/courses')

    if (response.status === 204) {
      courses.value = []
      return
    }

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`)
    }

    const data = await response.json()
    courses.value = data
  } catch (error) {
    console.error('Error fetching courses:', error)
    courses.value = []
    statusMessage.value = 'Failed to load courses. Please check if the backend is running.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.hello-container {
  padding: 40px;
  text-align: center;
}

.button-container {
  display: flex;
  gap: 15px;
  justify-content: center;
  padding: 20px 0;
}

.btn {
  padding: 8px 16px;
  cursor: pointer;
}

.btn:disabled {
  cursor: not-allowed;
  opacity: 0.6;
}

.status {
  color: #c0392b;
}

/* Improvement (b): modern table style */
.course-table {
  width: 80%;
  margin: 15px auto 0;
  border-collapse: collapse;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
}

.course-table th,
.course-table td {
  padding: 12px 15px;
  text-align: left;
}

.course-table th {
  background-color: #2c3e50;
  color: #ffffff;
  text-transform: uppercase;
  font-size: 0.85rem;
  letter-spacing: 0.05em;
}

.course-table tbody tr:nth-child(even) {
  background-color: #f6f8fa;
}

.course-table tbody tr:hover {
  background-color: #e3f2fd;
}

.course-table td {
  border-bottom: 1px solid #e0e0e0;
}
</style>