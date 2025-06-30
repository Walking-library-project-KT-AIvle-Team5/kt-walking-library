<template>
    <div>
        <Number
            label="ReadingActivityId"
            v-model="value.readingActivityId"
            :editMode="editMode"
        />
        <String
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <String
            label="BookId"
            v-model="value.bookId"
            :editMode="editMode"
        />
        <Number
            label="CurrentPage"
            v-model="value.currentPage"
            :editMode="editMode"
        />
        <String
            label="ReadingStatus"
            v-model="value.readingStatus"
            :editMode="editMode"
        />
        <Date
            label="StartedAt"
            v-model="value.startedAt"
            :editMode="editMode"
        />
        <Date
            label="CompletedAt"
            v-model="value.completedAt"
            :editMode="editMode"
        />
        <Date
            label="LastUpdatedAt"
            v-model="value.lastUpdatedAt"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'ReadingManagement',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "readingManagements",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
