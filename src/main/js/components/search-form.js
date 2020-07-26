import React from 'react';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';


class SearchForm extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        departure: '',
        arrival: '',
        from: '',
        to: ''
      };
  
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  
    handleChange(event) {
      this.setState({[event.target.name]: event.target.value});
    }
  
    handleSubmit(event) {
      event.preventDefault();
      console.log(this.state);
      const data = new FormData(event.target);
      fetch('/api/request-new-plan', {
        method: 'POST',
        body: data,
      });
    }
  
    render() {
      return (
        <div>
          <h2>Search for tours</h2>
          <Form onSubmit={this.handleSubmit}>
            <Form.Group as={Row} controlId="departure">
              <Form.Label column sm="2">
                Departure
              </Form.Label>
              <Col sm="10">
                  <Form.Control name="departure" as="select" value={this.state.departure} onChange={this.handleChange}>
                    <option>Dublin</option>
                    <option>London</option>
                    <option>Paris</option>
                    <option>Ho Chi Minh City</option>
                </Form.Control>
              </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="arrival">
              <Form.Label column sm="2">
                Arrival
              </Form.Label>
              <Col sm="10">
                  <Form.Control name="arrival" as="select" value={this.state.arrival} onChange={this.handleChange}>
                    <option>Dublin</option>
                    <option>London</option>
                    <option>Paris</option>
                    <option>Ho Chi Minh City</option>
                </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row} controlId="from">
              <Form.Label column sm="2">
                From
              </Form.Label>
              <Col sm="10">
                <Form.Control name="from" type="date" onChange={this.handleChange}>
                </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row} controlId="to">
              <Form.Label column sm="2">
                To
              </Form.Label>
              <Col sm="10">
                <Form.Control name="to" type="date" onChange={this.handleChange}>
                </Form.Control>
              </Col>
            </Form.Group>
            <Button variant="primary" type="submit">
              Submit
            </Button>
          </Form>
          <h2> Results</h2>
          <table class="table">
            <thead>
              <tr>
                <th scope="col">Provider</th>
                <th scope="col">Price</th>
                <th scope="col">Type</th>
                <th scope="col">Price</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <th scope="row">1</th>
                <td>Mark</td>
                <td>Otto</td>
                <td>@mdo</td>
              </tr>
              <tr>
                <th scope="row">2</th>
                <td>Jacob</td>
                <td>Thornton</td>
                <td>@fat</td>
              </tr>
              <tr>
                <th scope="row">3</th>
                <td>Larry</td>
                <td>the Bird</td>
                <td>@twitter</td>
              </tr>
            </tbody>
          </table>
        </div>
      );
    }
  }

  export default SearchForm;
