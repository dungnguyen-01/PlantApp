import moment from 'moment'
class formatDate {
  formatDate = (date) => {
    if (date === null) {
      return 'null'
    } else {
      var local = moment.utc(date).local().format('HH:mm:ss DD-MM-yyyy')
      return local
    }
  }
}
export default new formatDate()
