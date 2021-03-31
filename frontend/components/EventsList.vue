<template>
  <div class="events container">
    <h2 class="subtitle is-3">다음 사물함 오픈일</h2>
    <div class="columns is-multiline">
      <div v-for="event in events" :event="event" :key="event.id" class="column is-one-quarter">
        <router-link :to="`/event/${event.id}`">
          <EventCard :event="event" />
        </router-link>
      </div>
    </div>
  </div>
</template>
<script>
import EventCard from "@/components/EventCard";
export default {
  name: "EventsList",
  components: {
    EventCard
  },
  data() {
    return {
      event: {},
      events: [
        {
          id: 1,
          name: "산학융합관",
          category: "Fundraising",
          description:
            "Spend an elegant night of dinner and dancing with us as we raise money for our new rescue farm.",
          featuredImage: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500",
          images: [
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500",
          ],
          location: "한국산업기술대학교",
          date: "2021-03-31",
          time: "11:30"
        },
        {
          id: 2,
          name: "정왕역",
          category: "Adoptions",
          description:
            "Come to our donation drive to help us replenish our stock of pet food, toys, bedding, etc. We will have live bands, games, food trucks, and much more.",
          featuredImage: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500",
          images: ["https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500"],
          location: "경기도 시흥시 정왕동",
          date: "2021-04-31",
          time: "12:00"
        }
      ]
    };
  }
};
</script>
<style lang="scss" scoped>
.events {
  margin-top: 100px;
  text-align: center;
}
</style>