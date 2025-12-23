import React, { useState, useEffect} from 'react';
import './App.css';

function App(){

const [ students, setStudents] = useState([]);
const [loading, setLoading] = useState(true);
const [error, setError] = useState(null);
const [formData, setFormData] = useState({
    name: '',
    birthYear: '',
    major: '',
    isGraduate: false
});

const handleChange =  (e) => {
    const {name, value, type, checked} = e.target;
    setFormData(prev =>{
        return{
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        };
    });
};

    const handleSubmit = (e) => {
        e.preventDefault();

        const studentData = {
            ...formData,
            birthYear: parseInt(formData.birthYear)
        };

        fetch('http://localhost:8080/api/students', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(studentData)
        })
            .then(response=> {
                if (!response.ok) {
                    throw new Error('Failed to create student');
                }
                return response.json();
            })
            .then(() => {
                // Reset form
                setFormData({
                    name: '',
                    birthYear: '',
                    major: '',
                    isGraduate: false
                });
                // Refresh student list
                setLoading(true);
                fetch('http://localhost:8080/api/students')
                    .then(response => {
                        return response.json();
                    })
                    .then((data) => {
                        setStudents(data);
                        setLoading(false);
                    })
                    .catch((error)=> {
                        setError(error.message);
                        setLoading(false);
                    });
            })
            .catch((error)=> {
                alert('Error creating student: ' + error.message);
            });
    };

    const handleDelete = (studentID) => {
        if (!window.confirm('Are you sure you want to delete this student?')){
            return;
        }
        fetch('http://localhost:8080/api/students/' + studentID,{
            method: 'DELETE'
        })
        .then( response => {
            if (!response.ok){
                throw new Error ('Failed to delete student');
            }
            setStudents(prevStudents =>
                prevStudents.filter(s => s.studentID !== studentID)
            );
        })
            .catch(error => {
                console.error('Delete error:', error);
                alert('Error deleting student ' + error.message);
            });
    };

    useEffect(() => {
        fetch('http://localhost:8080/api/students')
            .then(response => response.json())
            .then(data => {
                setLoading(false);
                setStudents(data);
            })
            .catch(error => {
                setError(error.message);
                setLoading(false);
                console.error('Error fetching students:', error);
            });
    }, []);

    if (loading){
        return <div className="container">Loading students.... </div>;
    }
    if(error){
        return <div className="container">Error: {error}</div>
    }

    return(
        <div className ="container">
            <h1>Course Management System</h1>

            <div className="form-section">
                <h2>Add New Student</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Name:</label>
                        <input
                            type="text"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Birth Year:</label>
                        <input
                            type="number"
                            name="birthYear"
                            value={formData.birthYear}
                            onChange={handleChange}
                            min="1900"
                            max="2015"
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Major:</label>
                        <input
                            type="text"
                            name="major"
                            value={formData.major}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label>
                            <input
                                type="checkbox"
                                name="isGraduate"
                                checked={formData.isGraduate}
                                onChange={handleChange}
                            />
                            Graduate Student
                        </label>
                    </div>

                    <button type="submit">Add Student</button>
                </form>
            </div>

                <div className= 'student-list'>
                    <h2>Students ({students.length})</h2>
                    <ul>
                        {students.map(student =>(
                            <li key={student.studentID}>
                                <strong>{student.name}</strong> -  {student.major} ({student.birthYear})
                                {student.graduate && <span className="badge">Graduate</span>}
                                <button
                                    onClick={() => handleDelete(student.studentID)}
                                    className="delete-btn"
                                    >
                                    Delete
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
        </div>
    );
}

export default App;