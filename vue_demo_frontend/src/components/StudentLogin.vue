<template>
  <!-- Full-screen background wrapper -->
  <div class="login-container">
    <!-- Centered login form card -->
    <div class="edit-form">
      <h4>Login</h4>
      <form @submit.prevent="login">
        <div>
          <label for="studentId">Student ID: </label>
          <input type="text" id="studentId" v-model="studentLoginRequest.studentId"/>
        </div>
        <div>
          <label for="password">Password: </label>
          <input type="password" id="password" v-model="studentLoginRequest.password"/>
        </div>        
        <!-- Button group to place buttons side-by-side -->
        <div class="button-group">
          <button type="submit">Login</button>
          <!-- Changed type to "button" to prevent form auto-submission -->
          <button type="button" @click="openCreateModal" class="create-btn">Create</button>
        </div>
      </form> 
      <p v-if="message" style="color: red;">{{ message }}</p>
    </div>
    <!-- NEW: Create Student Info Modal Pop-up -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-card">
        <h4>Create Student Account</h4>
        <form @submit.prevent="submitRegistration">
          <div>
            <label for="newStudentId">Student ID: </label>
            <input type="text" id="newStudentId" v-model="newStudent.studentId" required />
          </div>
          <div>
            <label for="newPassword">Password: </label>
            <input type="password" id="newPassword" v-model="newStudent.password" required />
          </div>
          <div>
            <label for="studentName">Full Name: </label>
            <input type="text" id="studentName" v-model="newStudent.name" required />
          </div>
          <div>
            <label for="studentEmail">Email: </label>
            <input type="email" id="studentEmail" v-model="newStudent.email" required />
          </div>
          <!-- Modal Action Buttons -->
          <div class="button-group">
            <button type="submit" class="submit-btn">Submit</button>
            <button type="button" @click="closeCreateModal" class="cancel-btn">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import LoginService from "../services/LoginService";

export default {
  name: "studentLogin",
  data() {
    return {
      studentLoginRequest: {
        studentId: "",
        password: ""
      },
      message: "",
      // NEW: State for controlling the modal visibility
      showModal: false,
      // NEW: Data object to store registration details
      newStudent: {
        studentId: "",
        password: "",
        name: "",
        email: ""
      }
    };
  },
  methods: {
    login() {
      LoginService.login(this.studentLoginRequest)
        .then(response => {
          var student = response.data;
          console.log(student);
          localStorage.setItem("sid", student.id);
          this.message = ""; // Clear any previous error messages
          alert("success!");
          this.$router.push({ name: "courses" }); // Optional: keeps route navigation if your app uses it
        })
        .catch(e => {
          this.studentLoginRequest.studentId = "";
          this.studentLoginRequest.password = "";
          this.message = e.response?.data?.message || "Login failed, check internet or credential";
          console.error(e);
        });
    },
    // NEW: Open the registration form modal
    openCreateModal() {
      this.showModal = true;
    },    
    // NEW: Close the registration form modal and clear inputs
    closeCreateModal() {
      this.showModal = false;
      this.newStudent = { studentId: "", password: "", name: "", email: "" };
    },    
    // UPDATED FOR LAB 4: Connect to the registration API service
    submitRegistration() {
      console.log("Submitting new student data:", this.newStudent);

      LoginService.register(this.newStudent)
        .then(response => {
          alert(response.data.message || "Registration submitted successfully!");
          this.closeCreateModal(); // Close modal after successful submission
        })
        .catch(e => {
          const errorMsg = e.response?.data?.message || "Registration failed, please try again.";
          alert(errorMsg);
          console.error(e);
        });
    }
  },
  mounted() {
    this.message = "";
  }
};
</script>

<style scoped>
/* Main wrapper setting the full-screen view and background picture */
.login-container {
  width: 100vw;
  height: 100vh;
  background-image: url('../assets/Koala.jpg'); 
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
/* Form card layout */
.edit-form {
  max-width: 320px;
  width: 100%;
  padding: 25px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 8px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);
}
.edit-form div, .modal-card div {
  margin-bottom: 12px;
  display: flex;
  flex-direction: column; /* Stacks labels above input fields for cleaner look */
}
.edit-form label, .modal-card label {
  font-size: 0.9rem;
  margin-bottom: 4px;
  font-weight: bold;
}
.edit-form input, .modal-card input {
  padding: 6px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
/* Aligns buttons inline with equal distribution spacing */
.button-group {
  display: flex;
  gap: 10px;
  margin-top: 15px;
}
.button-group button {
  flex: 1;
  padding: 8px;
  cursor: pointer;
  border-radius: 4px;
}
.create-btn {
  background-color: #e0e0e0;
  border: 1px solid #ccc;
}
/* NEW: Full-screen semi-transparent overlay for the modal pop-up */
.modal-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* Dims the background image */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100; /* Keeps the modal on top of everything else */
}
/* NEW: Centered Pop-up Form Card */
.modal-card {
  max-width: 360px;
  width: 100%;
  padding: 25px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.3);
}
.submit-btn {
  background-color: #4CAF50;
  color: white;
  border: none;
}
.cancel-btn {
  background-color: #f44336;
  color: white;
  border: none;
}
</style>